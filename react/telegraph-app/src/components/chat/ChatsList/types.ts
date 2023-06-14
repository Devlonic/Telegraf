export interface IShortChat {
  id: number;
  title: string;
  type: string;
}

export interface IChatsList {
  list: IShortChat[];
}
