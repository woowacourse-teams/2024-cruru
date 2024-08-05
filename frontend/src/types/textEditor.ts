export type ToolbarOptions = (
  | string[]
  | {
      header: (number | boolean)[];
    }[]
  | {
      list: string;
    }[]
  | (
      | {
          align: never[];
        }
      | {
          color: never[];
        }
      | {
          background: never[];
        }
    )[]
)[];
