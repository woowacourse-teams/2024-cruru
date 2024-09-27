import { HttpResponse } from 'msw';

export const Success = () =>
  new HttpResponse(null, {
    status: 200,
  });

export const NotFoundError = () =>
  new HttpResponse(null, {
    status: 404,
  });

export const InternalServerError = () =>
  new HttpResponse(null, {
    status: 500,
  });
