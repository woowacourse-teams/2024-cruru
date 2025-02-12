export interface Email {
  subject: string;
  content: string;
  createdDate: string;
  isSucceed: boolean;
  optimistic?: boolean;
}
