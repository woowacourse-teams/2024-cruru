import applicantHandlers from './applicantHandlers';
import processHandlers from './processHandlers';
import evaluationHandlers from './evaluationHandlers';
import applyHandlers from './applyHandlers';
import dashboardHandlers from './dashboardHandlers';

const handlers = [...processHandlers, ...applicantHandlers, ...evaluationHandlers, ...dashboardHandlers, applyHandlers];

export default handlers;
