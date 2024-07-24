import styled from '@emotion/styled';

const Option = styled.div`
  display: flex;
  align-items: center;

  gap: 1.2rem;
`;

const Label = styled.span`
  ${({ theme }) => theme.typography.common.large}
`;

const S = {
  Option,
  Label,
};

export default S;
