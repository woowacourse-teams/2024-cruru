import { APPLICANTS } from './endPoint';

export const moveApplicant = async ({ processId, applicants }: { processId: number; applicants: number[] }) => {
  const response = await fetch(`${APPLICANTS}/move-process/${processId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      applicant_ids: applicants,
    }),
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return { status: response.status };
};
