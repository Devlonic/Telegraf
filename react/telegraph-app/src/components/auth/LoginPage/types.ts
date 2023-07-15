export interface ILoginRequest {
  username: string;
  password: string;
}

export interface ILoginRequestError {
  path: string;
  error: string;
  message: string;
  status: number;
}

export interface ILoginResponce {
  id: number;
  name: string;
  email: string;
  tokenType: string;
  accessToken: string;
}
