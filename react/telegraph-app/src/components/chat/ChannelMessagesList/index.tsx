import { useParams } from "react-router-dom";
import { IChatMessageAuthor, IChatMessagesList } from "./types";
import { useEffect, useRef, useState } from "react";
import classNames from "classnames";

const ChannelMessagesList = () => {
  const messagesDiv = useRef<HTMLDivElement>(null);

  const { id } = useParams();

  const [messages, setMessages] = useState<IChatMessagesList>({ list: [] });

  useEffect(() => {
    if (messagesDiv.current) {
      messagesDiv.current.scrollTop = messagesDiv.current.scrollHeight; // scroll to bottom of element
      console.log("yesmsgdiv");
    } else console.log("nomsgdiv");
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
        {
          id: 10,
          author: { id: 3, name: "zope234" },
          text:
            "hello asdfdsadasdasadsadsasdadsadsadsadsdasasdadsasdasdadsasddasasdasd!" +
            id,
          isSendedByRequester: false,
        },
        {
          id: 11,
          author: { id: 3, name: "Vasya228" },
          text:
            "hello asdfdsadasdasadsadsasdadsadsadsadsdasasdadsasdasdadsasddasasdasd!" +
            id,
          isSendedByRequester: true,
        },
        {
          id: 12,
          author: { id: 3, name: "zope234" },
          text:
            "hello asdfdsadasdasadsadsasdadsadsadsadsdasasdadsasdasdadsasddasasdasd!" +
            id,
          isSendedByRequester: false,
        },
        {
          id: 13,
          author: { id: 3, name: "zope234" },
          text:
            "hello asdfdsadasdasadsadsasdadsadsadsadsdasasdadsasdasdadsasddasasdasd!" +
            id,
          isSendedByRequester: false,
        },
        {
          id: 14,
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
      key={message.id}
      className={classNames("wrapper d-flex", {
        "justify-content-end": message.isSendedByRequester == true,
      })}
    >
      <div className="card m-3 bg-light" style={{ width: "18rem" }}>
        <div className="card-body">
          <h5 className="card-title">{message.author.name}</h5>
          <p className="card-text">{message.text}</p>
        </div>
      </div>
    </div>
  ));
  return (
    <>
      <div
        ref={messagesDiv}
        className="container-fluid overflow-y-scroll bg-black h-100"
      >
        <div className="container">{viewData}</div>
      </div>
    </>
  );
};

export default ChannelMessagesList;
