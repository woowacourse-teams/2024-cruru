import { EMAILS } from '../endPoint';
import APIClient from '../APIClient';

const apiClient = new APIClient(EMAILS);

const emailApis = {
  send: async ({
    clubId,
    applicantIds,
    subject,
    content,
  }: {
    clubId: string;
    applicantIds: number[];
    subject: string;
    content: string;
  }) =>
    apiClient.post({
      path: '/send',
      body: { clubId, applicantIds, subject, content },
      isFormData: true,
    }),

  postVerifyMail: async ({ email }: { email: string }) =>
    apiClient.post({
      path: '/verification-code',
      body: { email },
    }),

  confirmVerifyCode: async ({ email, verificationCode }: { email: string; verificationCode: string }) =>
    apiClient.post({
      path: '/verify-code',
      body: { email, verificationCode },
    }),
};

export default emailApis;
