/* eslint-disable no-promise-executor-return */
import { MEMBERS } from '@api/endPoint';
import { http } from 'msw';
import { Success } from './response';

interface LoginFormData {
  clubName: string;
  email: string;
  password: string;
  phone: string;
}

const membersHandlers = [
  http.post(`${MEMBERS}/signup`, async ({ request }) => {
    const body = (await request.json()) as LoginFormData;

    await new Promise((resolve) => setTimeout(resolve, 2000));

    if (!body.email || !body.password || !body.clubName || !body.phone) {
      return new Response(null, {
        status: 401,
        statusText: '[Mock Data Error] Sign Up Failed',
      });
    }

    return Success();
  }),
];

export default membersHandlers;
