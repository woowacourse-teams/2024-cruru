import applicantHandlers from './applicantHandlers';
import processHandlers from './processHandlers';
import evaluationHandlers from './evaluationHandlers';
import applyHandlers from './applyHandlers';

const handlers = [...processHandlers, ...applicantHandlers, ...evaluationHandlers, ...applyHandlers];

export default handlers;
