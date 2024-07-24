import applicantHandlers from './applicantHandlers';
import processHandlers from './processHandlers';

const handlers = [...processHandlers, ...applicantHandlers];

export default handlers;
