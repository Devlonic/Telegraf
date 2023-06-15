import { AxiosError } from "axios";
import { chatsHttp } from "./../tokenService";
import {
  IChatPreview,
  ICreateChatRequest,
  ICreateChatResponce,
  IGetChatsListRequest,
} from "./types";

export const createChatAsync = async (
  data: ICreateChatRequest
): Promise<ICreateChatResponce> => {
  try {
    var resp = await chatsHttp.post(`create`, data);
    var respData = resp.data as ICreateChatResponce;
    console.log("createChatAsync resp = ", respData);
    return respData;
  } catch (e: any) {
    const er = e as AxiosError;
    console.log("Server error", er);
    throw er;
  }
};

export const getAllChatsAsync = async (
  data: IGetChatsListRequest
): Promise<IChatPreview[]> => {
  try {
    var resp = await chatsHttp.get(``, data);
    var respData = resp.data as IChatPreview[];
    console.log("createChatAsync resp = ", respData);
    return respData;
  } catch (e: any) {
    const er = e as AxiosError;
    console.log("Server error", er);
    throw er;
  }
};
