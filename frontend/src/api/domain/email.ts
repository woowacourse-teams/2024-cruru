import { EMAILS } from '../endPoint';
import APIClient from '../APIClient';

const apiClient = new APIClient(EMAILS);

const emailApis = {
  send: async ({
    clubId,
    applicantId,
    subject,
    content,
  }: {
    clubId: string;
    applicantId: number;
    subject: string;
    content: string;
  }) =>
    apiClient.post({
      path: '/send',
      body: { clubId, applicantIds: applicantId, subject, content },
      isFormData: true,
    }),
};

export default emailApis;
