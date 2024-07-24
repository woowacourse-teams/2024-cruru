import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px;
  border-radius: 8px;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[800]}
  margin-bottom: 20px;
`;

const ActionRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0px 4px 16px 4px;
  height: 48px;
`;

const DetailContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  border-top: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};

  padding: 8px 0px;
`;

const DetailRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Label = styled.span`
  ${({ theme }) => theme.typography.common.largeBlock}
`;

const Value = styled.span`
  ${({ theme }) => theme.typography.common.smallBlock}
  color: ${({ theme }) => theme.baseColors.grayscale[700]};
`;

const S = {
  Container,
  Title,
  ActionRow,
  DetailContainer,
  DetailRow,
  Label,
  Value,
};

export default S;
