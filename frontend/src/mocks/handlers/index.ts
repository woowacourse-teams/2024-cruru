import applicantHandlers from './applicantHandlers';
import processHandlers from './processHandlers';
import evaluationHandlers from './evaluationHandlers';

const handlers = [...processHandlers, ...applicantHandlers, ...evaluationHandlers];

export default handlers;
