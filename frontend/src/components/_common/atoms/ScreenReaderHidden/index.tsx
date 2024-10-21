import styled from '@emotion/styled';
import { AriaAttributes, PropsWithChildren } from 'react';

const HiddenSpan = styled.span`
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
`;

/**
 * HiddenElementForSR 컴포넌트
 *
 * 이 컴포넌트는 내용을 시각적으로 숨기지만,
 * 스크린 리더(Screen Reader)에서는 읽을 수 있게 합니다.
 *
 * @example
 * <label>
 *   이메일
 *   <HiddenElementForSR>(필수)</HiddenElementForSR>
 *   <input type="email" required />
 * </label>
 */
export default function HiddenElementForSR({ children, ...props }: PropsWithChildren & AriaAttributes) {
  return <HiddenSpan {...props}>{children}</HiddenSpan>;
}
