import { Link, useParams } from "react-router-dom";
import "./style.css";
import { useEffect, useState } from "react";
import classNames from "classnames";
import { getAllChatsAsync } from "../../../services/chat/chatService";
import { AxiosError } from "axios";
import {
  IChatPreview,
  IGetChatsListRequestError,
} from "../../../services/chat/types";
import ReactLoading from "react-loading";

const ChatsList = () => {
  const { id } = useParams();

  const [chats, setChats] = useState<IChatPreview[]>([]);
  const [isProcessing, setIsProcessing] = useState<boolean>(false);
  const [responceError, setResponceError] = useState<string>();
  useEffect(() => {
    const fetchData = async () => {
      try {
        await setIsProcessing(true);
        var respData = await getAllChatsAsync({});
        console.log(" ChatsList resp = ", respData);
        await setChats(respData);
        await setIsProcessing(false);
      } catch (e: any) {
        await setIsProcessing(false);
        const er = e as AxiosError;
        let errors = er?.response?.data as IGetChatsListRequestError;
        if (errors == null) setResponceError(er.message);
        else setResponceError(errors.message);

        console.log("Server error", er);
      }
    };
    fetchData();
  }, []);

  let localID = -1;
  if (id != undefined) localID = parseInt(id);

  const viewData = chats.map((chat) => (
    <Link
      key={chat.id}
      to={`/chat/channel/${chat.id}`}
      className={classNames("list-group-item list-group-item-action", {
        active: chat.id == localID,
      })}
      // aria-current="true"
    >
      <div className="row">
        <div className="col-4">
          <div className="circle"></div>
        </div>
        <div className="col">
          <div className="row h-50">
            <span className="text-black fs-4">{chat.name}</span>
          </div>
        </div>
      </div>
    </Link>
  ));

  return (
    <div className="dialogs w-100">
      {isProcessing && (
        <div className="wrapper">
          <div className="row">
            <div className="col"></div>
            <div className="col">
              <div className="d-flex justify-content-center">
                <ReactLoading
                  type="bars"
                  color="gray"
                  height={"100%"}
                  width={"100%"}
                ></ReactLoading>
              </div>
            </div>
            <div className="col"></div>
          </div>
        </div>
      )}
      {!isProcessing && (
        <>
          <div className="scearch-form-wrapper p-1">
            <form className="d-flex" role="search">
              <input
                className="form-control me-2"
                type="search"
                placeholder="Search"
                aria-label="Search"
              />
              <button className="btn btn-success" type="submit">
                Пошук
              </button>
            </form>
          </div>

          <div className="list-group h-80-vh">{viewData}</div>
        </>
      )}
    </div>
  );
};

export default ChatsList;
