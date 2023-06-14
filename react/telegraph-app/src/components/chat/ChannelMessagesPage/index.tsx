import { useParams } from "react-router-dom";
import { IChatMessageAuthor, IChatMessagesList } from "./types";
import { useEffect, useState } from "react";
import classNames from "classnames";

const ChannelMessagesPage = () => {
  const { id } = useParams();

  const [messages, setMessages] = useState<IChatMessagesList>({ list: [] });

  useEffect(() => {
    // todo load messages from API
    setMessages({
      list: [
        {
          id: 0,
          author: { id: 0, name: "Vasya228" },
          text: "hello world!" + id,
          isSendedByRequester: false,
        },
        {
          id: 1,
          author: { id: 0, name: "Vasya228" },
          text: "hello world1!" + id,
          isSendedByRequester: false,
        },
        {
          id: 2,
          author: { id: 0, name: "Vasya228" },
          text: "hello world2!" + id,
          isSendedByRequester: false,
        },
        {
          id: 3,
          author: { id: 0, name: "Vasya228" },
          text: "hello world3!" + id,
          isSendedByRequester: false,
        },
        {
          id: 4,
          author: { id: 1, name: "kakakika123321" },
          text: "hello world4!" + id,
          isSendedByRequester: true,
        },
        {
          id: 5,
          author: { id: 1, name: "kakakika123321" },
          text: "hello world5!" + id,
          isSendedByRequester: true,
        },
        {
          id: 6,
          author: { id: 1, name: "kakakika123321" },
          text: "hello world6!" + id,
          isSendedByRequester: true,
        },
        {
          id: 7,
          author: { id: 3, name: "zope234" },
          text:
            "hello asdfdsadasdasadsadsasdadsadsadsadsdasasdadsasdasdadsasddasasdasd!" +
            id,
          isSendedByRequester: false,
        },
      ],
    });
  }, [id]);
  const viewData = messages.list.map((message) => (
    <div
      className={classNames("wrapper d-flex", {
        "justify-content-end": message.isSendedByRequester == true,
      })}
    >
      <div
        key={message.id}
        className="card m-3 bg-light"
        style={{ width: "18rem" }}
      >
        <div className="card-body">
          <h5 className="card-title">{message.author.name}</h5>
          <p className="card-text">{message.text}</p>
        </div>
      </div>
    </div>
  ));
  return (
    <>
      <div className="container-fluid overflow-y-scroll h-100">
        <div className="container">{viewData}</div>
      </div>
    </>
  );
};

export default ChannelMessagesPage;
