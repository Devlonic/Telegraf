export interface IRegistrationRequest {
  username: string;
  email: string;
  password: string;
}

export interface IRegistrationRequestError {
  message: string;
}

export interface IRegistrationResponce {
  message: string;
}
