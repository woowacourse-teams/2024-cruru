import styled from '@emotion/styled';

const LogoutButton = styled.button`
  ${({ theme }) => theme.typography.common.default};
  color: ${({ theme }) => theme.baseColors.grayscale[600]};

  position: absolute;
  bottom: 2.4rem;
  left: 50%;
  transform: translateX(-50%);
`;

const S = {
  LogoutButton,
};

export default S;
