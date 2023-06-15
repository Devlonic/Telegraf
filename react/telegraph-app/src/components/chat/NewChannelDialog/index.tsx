import "./style.css";
import Cropper from "cropperjs";
import "cropperjs/dist/cropper.css";
import classNames from "classnames";
import { LegacyRef, useEffect, useRef, useState } from "react";
import { INewChannelModal } from "./types";
import {
  ICreateChatRequest,
  ICreateChatRequestError,
} from "../../../services/chat/types";
import * as yup from "yup";
import { useFormik } from "formik";
import { createChatAsync } from "../../../services/chat/chatService";
import { AxiosError } from "axios";
import ReactLoading from "react-loading";

const NewChannelDialog: React.FC<INewChannelModal> = ({
  text = "Are you sure you want to do this?!",
  onConfirm = null,
  onCancel = null,
  isShown = false,
}) => {
  const [shown, setShown] = useState<boolean>(isShown);

  useEffect(() => {
    console.log("NewChannelDialog useeffect");
    setShown((prev) => isShown);
  }, [isShown]);

  // show/hide modal window
  const toggleModal = async () => {
    await setShown((prev) => !prev);
  };

  const onClickConfirm = async () => {
    if (onConfirm) await onConfirm();
    await toggleModal();
  };

  const onClickCancel = async () => {
    if (onCancel) await onCancel();
    await toggleModal();
  };

  const initValues: ICreateChatRequest = {
    name: "",
  };

  const loginSchema = yup.object({
    name: yup.string().required("Enter chat name"),
  });

  const [responceError, setResponceError] = useState<string>();
  const [responceSuccess, setResponceSuccess] = useState<string>();
  const [isProcessing, setIsProcessing] = useState<boolean>(false);

  const onSubmitFormikData = async (values: ICreateChatRequest) => {
    try {
      await setIsProcessing(true);
      console.log("newchannel");
      var respData = await createChatAsync(values);
      console.log("resp = ", respData);
      setResponceSuccess(respData.message);
      await setIsProcessing(false);
    } catch (e: any) {
      await setIsProcessing(false);
      const er = e as AxiosError;

      let errors = er?.response?.data as ICreateChatRequestError;

      if (errors == null) setResponceError(er.message);
      else setResponceError(errors.message);

      console.log("Server error", er);
    }
  };

  const formik = useFormik({
    initialValues: initValues,
    validationSchema: loginSchema,
    onSubmit: onSubmitFormikData,
  });

  const { values, errors, touched, handleSubmit, handleChange } = formik;

  return (
    <>
      {/* modal body */}
      <div
        className={classNames("modal", { "custom-modal": shown })}
        tabIndex={-1}
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="exampleModalLabel">
                Create new channel
              </h5>
            </div>
            <div className="modal-body">
              <div className="d-flex justify-content-center">
                <div className="w-100">
                  {responceError && (
                    <div className="alert alert-danger" role="alert">
                      {responceError}
                    </div>
                  )}
                  {responceSuccess && (
                    <div className="alert alert-success" role="alert">
                      {responceSuccess}
                    </div>
                  )}
                  {isProcessing && (
                    <div className="wrapper">
                      <div className="row">
                        <div className="col"></div>
                        <div className="col">
                          <div className="d-flex justify-content-center">
                            <ReactLoading
                              type="bars"
                              color="gray"
                              height={"50%"}
                              width={"50%"}
                            ></ReactLoading>
                          </div>
                        </div>
                        <div className="col"></div>
                      </div>
                    </div>
                  )}
                  {!isProcessing && (
                    <form onSubmit={handleSubmit}>
                      <div className="wrapper">
                        <div className="mb-3">
                          <label htmlFor="name" className="form-label">
                            Name
                          </label>
                          <input
                            type="text"
                            className={classNames("form-control", {
                              "is-invalid": errors.name && touched.name,
                            })}
                            id="name"
                            name="name"
                            value={values.name}
                            onChange={handleChange}
                          />
                          {errors.name && touched.name && (
                            <div className="invalid-feedback">
                              {errors.name}
                            </div>
                          )}
                        </div>
                        <button type="submit" className="btn btn-primary w-100">
                          Create channel
                        </button>
                        {/* {errors.error && <div className="text-danger">{errors.error}</div>} */}
                      </div>
                    </form>
                  )}
                </div>
              </div>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
                onClick={onClickCancel}
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default NewChannelDialog;
