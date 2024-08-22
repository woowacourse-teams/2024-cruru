import applicantHandlers from './applicantHandlers';
import processHandlers from './processHandlers';
import evaluationHandlers from './evaluationHandlers';
import applyHandlers from './applyHandlers';
import dashboardHandlers from './dashboardHandlers';
import authHandlers from './authHandlers';
import membersHandlers from './memberHandlers';
import questionHandlers from './questionHandlers';

const handlers = [
  ...processHandlers,
  ...applicantHandlers,
  ...evaluationHandlers,
  ...dashboardHandlers,
  ...applyHandlers,
  ...authHandlers,
  ...membersHandlers,
  ...questionHandlers,
];

export default handlers;
