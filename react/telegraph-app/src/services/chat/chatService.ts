import { AxiosError } from "axios";
import { chatsHttp } from "./../tokenService";
import { ICreateChatRequest, ICreateChatResponce } from "./types";

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
