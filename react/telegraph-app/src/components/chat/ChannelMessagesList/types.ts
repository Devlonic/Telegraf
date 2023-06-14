export interface IChatMessageAuthor {
  id: number;
  name: string;
}

export interface IChatMessage {
  id: number;
  author: IChatMessageAuthor;
  text: string;
  isSendedByRequester: boolean;
}

export interface IChatMessagesList {
  list: IChatMessage[];
}

export interface IChatSendRequest {
  chat_id: number;
  text: string;
}
