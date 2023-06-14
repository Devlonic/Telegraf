import axios from "axios";
import jwtDecode from "jwt-decode";
import { APP_ENV } from "../env";

export const storeToken = (token: string) => {
  localStorage.setItem("token", token);
  usersHttp = axios.create({
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  });
};

export const getToken = () => {
  return localStorage.getItem("token");
};

export const removeToken = () => {
  return localStorage.removeItem("token");
};

export const decodeToken = (token: string) => {
  return jwtDecode(token);
  // return jwt.decode(token);
};

export const isSignedIn = (): boolean => {
  let t = getToken();
  // todo add overdue check
  return t != null && t != "" && t != undefined;
};

export var usersHttp = axios.create({
  baseURL: APP_ENV.USERS_API,
  headers: {
    Authorization: `Bearer ${getToken()}`,
  },
});
