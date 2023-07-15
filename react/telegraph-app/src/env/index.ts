const USERS_API: string = process.env.REACT_APP_USERS_API as string;
const CHATS_API: string = process.env.REACT_APP_CHATS_API as string;

const APP_ENV = {
  USERS_API: USERS_API,
  CHATS_API: CHATS_API,
};
export { APP_ENV };
