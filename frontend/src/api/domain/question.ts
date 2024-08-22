import { QUESTIONS } from '@api/endPoint';
import { convertParamsToQueryString } from '@api/utils';
import { ModifyQuestionData } from '@customTypes/question';

import APIClient from '@api/APIClient';

const apiClient = new APIClient(QUESTIONS);

const questionApis = {
  patch: async ({ applyformId, questions }: { applyformId: string; questions: ModifyQuestionData[] }) =>
    apiClient.patch({
      path: `?${convertParamsToQueryString({
        applyformId,
      })}`,
      body: { questions },
    }),
};

export default questionApis;
