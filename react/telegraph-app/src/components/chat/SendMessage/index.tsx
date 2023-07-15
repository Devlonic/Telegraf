import { useParams } from "react-router-dom";
import {
  IChatMessageAuthor,
  IChatMessagesList,
  IChatSendRequest,
} from "./types";
import { useState } from "react";
import classNames from "classnames";
import { useFormik } from "formik";
import * as yup from "yup";
import { AxiosError } from "axios";
import ReactLoading from "react-loading";
import { delay } from "../../../services/delayService";

const SendMessage = () => {
  const { id } = useParams();

  const initValues: IChatSendRequest = {
    chat_id: 0,
    message_text: "",
  };

  const messageSchema = yup.object({
    message_text: yup.string().required("Enter message"),
  });
  const [responceError, setResponceError] = useState<string>();
  const [isProcessing, setIsProcessing] = useState<boolean>(false);

  const onSubmitFormikData = async (msg: IChatSendRequest) => {
    try {
      await setIsProcessing(true);
      console.log("submit");
      // var resp = await usersHttp.post(`signin`, values);
      // var respData = resp.data as ILoginResponce;
      // console.log("resp = ", respData);
      values.message_text = "";
      await delay(3000);
      await setIsProcessing(false);
    } catch (e: any) {
      await setIsProcessing(false);
      const er = e as AxiosError;

      let errors = er?.response?.data as string;

      if (errors == null) setResponceError(er.message);
      else setResponceError(errors);

      console.log("Server error", er);
    }
  };

  const formik = useFormik({
    initialValues: initValues,
    validationSchema: messageSchema,
    onSubmit: onSubmitFormikData,
  });

  const { values, errors, touched, handleSubmit, handleChange } = formik;
  return (
    <>
      <form onSubmit={handleSubmit} className="d-flex h-100">
        <input
          type="text"
          id="message_text"
          className={classNames("form-control h-50 bg-dark fs-2 text-white")}
          name="message_text"
          value={values.message_text}
          onChange={handleChange}
        />
        <button type="submit" className="btn btn-success w-25 h-50">
          {!isProcessing && <>Send</>}
          {isProcessing && (
            <div className="wrapper">
              <div className="row">
                <div className="col"></div>
                <div className="col">
                  <div className="d-flex justify-content-center">
                    <ReactLoading
                      type="bars"
                      color="white"
                      height={"80%"}
                      width={"80%"}
                    ></ReactLoading>
                  </div>
                </div>
                <div className="col"></div>
              </div>
            </div>
          )}
        </button>
      </form>
    </>
  );
};

export default SendMessage;
