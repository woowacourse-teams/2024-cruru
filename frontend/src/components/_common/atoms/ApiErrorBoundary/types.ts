import ApiError from '@api/ApiError';
import { FallbackProps } from 'react-error-boundary';

/**
 * ApiErrorBoundary의 FallbackComponent에 전달되는 props
 */
export interface ApiFallbackProps extends FallbackProps {
  error: ApiError;
}
