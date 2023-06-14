import { Link, useParams } from "react-router-dom";
import "./style.css";
import { useEffect, useState } from "react";
import { IChatsList } from "./types";
import classNames from "classnames";

const ChatsList = () => {
  const { id } = useParams();

  const [chats, setChats] = useState<IChatsList>({ list: [] });

  useEffect(() => {
    // todo load chats from API

    setChats({
      list: [
        { id: 0, title: "Channel 1", type: "channel" },
        { id: 1, title: "Channel 2", type: "channel" },
        { id: 2, title: "Channel 3", type: "channel" },
        { id: 3, title: "Channel 4", type: "channel" },
        { id: 4, title: "Channel 5", type: "channel" },
        { id: 5, title: "Channel 6", type: "channel" },
        { id: 6, title: "Channel 7", type: "channel" },
        { id: 7, title: "Channel 8", type: "channel" },
        { id: 8, title: "Channel 9", type: "channel" },
        { id: 9, title: "Channel 10", type: "channel" },
      ],
    });
  });

  let localID = -1;
  if (id != undefined) localID = parseInt(id);

  const viewData = chats.list.map((chat) => (
    <Link
      key={chat.id}
      to={`/chat/${chat.type}/${chat.id}`}
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
            <span className="text-black fs-4">{chat.title}</span>
          </div>
        </div>
      </div>
    </Link>
  ));

  return (
    <div className="dialogs w-100">
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
    </div>
  );
};

export default ChatsList;
