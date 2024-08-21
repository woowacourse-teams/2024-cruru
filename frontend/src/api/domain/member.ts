import { MEMBERS } from '@api/endPoint';
import APIClient from '../APIClient';

const apiClient = new APIClient(MEMBERS);

const membersApi = {
  signUp: async ({
    clubName,
    email,
    password,
    phone,
  }: {
    clubName: string;
    email: string;
    password: string;
    phone: string;
  }) =>
    apiClient.post({
      path: '/signup',
      body: { clubName, email, password, phone },
    }),
};

export default membersApi;
