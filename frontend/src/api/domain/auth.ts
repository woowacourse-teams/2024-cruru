import { AUTH } from '../endPoint';
import APIClient from '../APIClient';

const apiClient = new APIClient(AUTH);

const authApi = {
  login: async ({ email, password }: { email: string; password: string }) =>
    apiClient.post<{ clubId: string }>({
      path: '/login',
      body: { email, password },
    }),
};

export default authApi;
