import { EvaluationResult } from '@customTypes/applicant';
import { convertParamsToQueryString } from '../utils';
import { EVALUATIONS } from '../endPoint';
import APIClient from '../APIClient';

const apiClient = new APIClient(EVALUATIONS);

const evaluationApis = {
  get: async ({ processId, applicantId }: { processId: number; applicantId: number }) => {
    const queryParams = {
      processId: processId.toString(),
      applicantId: applicantId.toString(),
    };

    return apiClient.get<{ evaluations: EvaluationResult[] }>({
      path: `?${convertParamsToQueryString(queryParams)}`,
    });
  },

  create: async ({
    processId,
    applicantId,
    evaluator,
    score,
    content,
  }: {
    processId: number;
    applicantId: number;
    evaluator: string;
    score: number;
    content: string;
  }) =>
    apiClient.post({
      path: `?${convertParamsToQueryString({
        processId: processId.toString(),
        applicantId: applicantId.toString(),
      })}`,
      body: { evaluator, score, content },
    }),

  delete: async ({ evaluationId }: { evaluationId: number }) =>
    apiClient.delete({
      path: `/${evaluationId}`,
    }),
};

export default evaluationApis;
