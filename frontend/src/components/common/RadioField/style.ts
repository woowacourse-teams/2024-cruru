import styled from '@emotion/styled';

const Container = styled.div<{ optionsGap: string | undefined }>`
  display: flex;
  flex-direction: column;

  gap: ${({ optionsGap }) => optionsGap ?? '1.6rem'};
`;

const S = {
  Container,
};

export default S;
