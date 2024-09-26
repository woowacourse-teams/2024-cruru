import { http } from 'msw';
import { EMAILS } from '@api/endPoint';

const emailHandlers = [
  http.post(`${EMAILS}/send`, async ({ request }) => {
    const body = (await request.formData()) as FormData;

    if (!body.get('subject') || !body.get('content')) {
      return new Response(null, {
        status: 400,
        statusText: 'malformed request body syntax',
      });
    }

    return new Response(null, {
      status: 200,
    });
  }),
];

export default emailHandlers;
