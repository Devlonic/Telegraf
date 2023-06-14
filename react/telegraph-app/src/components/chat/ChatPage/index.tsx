import { useParams } from "react-router-dom";
import { useEffect, useRef, useState } from "react";
import classNames from "classnames";
import ChannelMessagesList from "../ChannelMessagesList";
import "./styles.css";
import SendMessage from "../SendMessage";
const ChatPage = () => {
  const messagesDiv = useRef<HTMLDivElement>(null);

  const { id } = useParams();

  useEffect(() => {}, [id]);

  return (
    <>
      <div className="h-85">
        <ChannelMessagesList></ChannelMessagesList>
      </div>
      <div className="h-15">
        <SendMessage></SendMessage>
      </div>
    </>
  );
};

export default ChatPage;
