import classNames from "classnames";
import { ChangeEvent, useEffect, useState } from "react";
import ReactLoading from "react-loading";
import { useNavigate } from "react-router-dom";
import { ILoginRequest, ILoginRequestError, ILoginResponce } from "./types";
import { APP_ENV } from "../../../env";
import {
  usersHttp,
  isSignedIn,
  storeToken,
} from "../../../services/tokenService";
import { AxiosError } from "axios";
import * as yup from "yup";
import { useFormik } from "formik";

const LoginPage = () => {
  const navigator = useNavigate();

  useEffect(() => {
    if (isSignedIn() == true) {
      navigator("/");
    }
  });

  const initValues: ILoginRequest = {
    username: "",
    password: "",
  };

  const loginSchema = yup.object({
    username: yup.string().required("Enter username"),
    password: yup.string().required("Enter password"),
  });

  const [responceError, setResponceError] = useState<string>();
  const [isProcessing, setIsProcessing] = useState<boolean>(false);

  const onSubmitFormikData = async (values: ILoginRequest) => {
    try {
      await setIsProcessing(true);
      var resp = await usersHttp.post(`signin`, values);
      var respData = resp.data as ILoginResponce;
      console.log("resp = ", respData);
      storeToken(`${respData.tokenType} ${respData.accessToken}`);

      await setIsProcessing(false);
    } catch (e: any) {
      await setIsProcessing(false);
      const er = e as AxiosError;

      let errors = er?.response?.data as ILoginRequestError;

      if (errors == null) setResponceError(er.message);
      else setResponceError(errors.error);

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
    <div className="d-flex justify-content-center">
      <div className="w-25">
        <h1 className="text-center">Login</h1>
        {responceError && (
          <div className="alert alert-danger" role="alert">
            {responceError}
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
                <label htmlFor="email" className="form-label">
                  Username
                </label>
                <input
                  type="text"
                  className={classNames("form-control", {
                    "is-invalid": errors.username && touched.username,
                  })}
                  id="username"
                  name="username"
                  value={values.username}
                  onChange={handleChange}
                />
                {errors.username && touched.username && (
                  <div className="invalid-feedback">{errors.username}</div>
                )}
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Password
                </label>
                <input
                  type="password"
                  id="password"
                  className={classNames("form-control", {
                    "is-invalid": errors.password,
                  })}
                  name="password"
                  value={values.password}
                  onChange={handleChange}
                />
                {errors.password && touched.password && (
                  <div className="invalid-feedback">{errors.password}</div>
                )}
              </div>
              <button type="submit" className="btn btn-primary w-100">
                Sign in
              </button>
              {/* {errors.error && <div className="text-danger">{errors.error}</div>} */}
            </div>
          </form>
        )}
      </div>
    </div>
  );
};

export default LoginPage;
