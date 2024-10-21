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
};

export default emailApis;
