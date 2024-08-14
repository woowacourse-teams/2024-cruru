import styled from '@emotion/styled';

const Option = styled.div`
  display: flex;
  align-items: center;

  gap: 1rem;
`;

const Label = styled.span<{ labelSize: string | undefined }>`
  ${({ theme }) => theme.typography.common.large};
  font-size: ${({ labelSize }) => labelSize ?? '1.6rem'};
`;

const S = {
  Option,
  Label,
};

export default S;
