import { Outlet, useNavigate } from "react-router-dom";
import DefaultHeader from "./DefaultHeader";
import { useEffect } from "react";
import { isSignedIn } from "../../../services/tokenService";
import ChatsList from "../../chat/ChatsList";

const DefaultLayout = () => {
  const navigator = useNavigate();

  useEffect(() => {
    console.log("DefaultLayout useEffect");
    if (isSignedIn() == false) {
      console.log("token lost. redirect to auth");
      navigator("/auth/login");
    }
  }, []);

  return (
    <div className="h-100">
      <DefaultHeader />
      <div className="container-fluid h-100">
        <div className="row h-100">
          <div className="col-2 h-100 p-0 bg-dark">
            <ChatsList />
          </div>
          <div className="col h-100 p-0">
            <Outlet />
          </div>
        </div>
      </div>
    </div>
  );
};
export default DefaultLayout;
