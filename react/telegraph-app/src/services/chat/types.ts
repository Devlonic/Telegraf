export interface ICreateChatRequest {
  name: string;
}
export interface ICreateChatResponce {
  message: string;
}
export interface ICreateChatRequestError {
  message: string;
}

export interface IGetChatsListRequest {}

export interface IGetChatsListRequestError {
  message: string;
}

export interface IChatPreview {
  id: number;
  name: string;
}
