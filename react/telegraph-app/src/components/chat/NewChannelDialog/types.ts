export interface INewChannelModal {
  text?: string;
  onConfirm?: () => void;
  onCancel?: () => void;
  isShown: boolean;
}
