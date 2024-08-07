import applicantHandlers from './applicantHandlers';
import processHandlers from './processHandlers';
import evaluationHandlers from './evaluationHandlers';
import dashboardHandlers from './dashboardHandlers';

const handlers = [...processHandlers, ...applicantHandlers, ...evaluationHandlers, ...dashboardHandlers];

export default handlers;
