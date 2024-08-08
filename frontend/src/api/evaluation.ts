import { convertParamsToQueryString } from './utils';
import { EVALUATIONS } from './endPoint';

const evaluationApis = {
  get: async ({ processId, applicantId }: { processId: number; applicantId: number }) => {
    const queryParams = {
      processId: processId.toString(),
      applicantId: applicantId.toString(),
    };

    const response = await fetch(`${EVALUATIONS}?${convertParamsToQueryString(queryParams)}`, {
      headers: {
        Accept: 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const data = await response.json();
    return data;
  },

  create: async ({
    processId,
    applicantId,
    score,
    content,
  }: {
    processId: number;
    applicantId: number;
    score: string;
    content: string;
  }) => {
    const queryParams = {
      processId: processId.toString(),
      applicantId: applicantId.toString(),
    };

    const response = await fetch(`${EVALUATIONS}?${convertParamsToQueryString(queryParams)}`, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json',
      },
      body: JSON.stringify({ score, content }),
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    return response;
  },
};

export default evaluationApis;
