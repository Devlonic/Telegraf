import { Link } from "react-router-dom";
import DangerDialog from "../../common/DangerDialog";
import { useState } from "react";
import NewChannelDialog from "../../chat/NewChannelDialog";

const DefaultHeader = () => {
  const [isNewChannelDialogShown, setIsNewChannelDialogShown] =
    useState<boolean>(false);

  const onNewChannelClick = () => {
    setIsNewChannelDialogShown(!isNewChannelDialogShown);
  };

  return (
    <header>
      <NewChannelDialog isShown={isNewChannelDialogShown}></NewChannelDialog>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container">
          <Link className="navbar-brand" to="/">
            Telegraf
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link className="nav-link active" aria-current="page" to="/">
                  Home
                </Link>
              </li>
            </ul>

            <button
              onClick={onNewChannelClick}
              className="btn btn-outline-primary"
            >
              Create new channel
            </button>

            <Link
              className="btn btn-outline-secondary"
              aria-current="page"
              to="/auth/signout"
            >
              Sign out
            </Link>
          </div>
        </div>
      </nav>
    </header>
  );
};

export default DefaultHeader;
