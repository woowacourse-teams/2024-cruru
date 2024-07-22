export const Success = new Response(null, {
  status: 200,
});

export const NotFoundError = new Response(null, {
  status: 404,
});

export const InternalServerError = new Response(null, {
  status: 500,
});
