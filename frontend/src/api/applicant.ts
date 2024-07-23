import snakeToCamel from '@utils/snakeToCamel';
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

export const getSpecificApplicant = async ({ applicantId }: { applicantId: number }) => {
  const response = await fetch(`${APPLICANTS}/${applicantId}`, {
    headers: {
      Accept: 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const data = await response.json();
  return snakeToCamel(data);
};
