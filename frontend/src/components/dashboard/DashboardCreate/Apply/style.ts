import styled from '@emotion/styled';

const Wrapper = styled.main`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

const Section = styled.section`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

const SectionTitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  ${({ theme }) => theme.typography.common.default};

  h2 {
    ${({ theme }) => theme.typography.heading[500]};
  }

  span {
    color: ${({ theme }) => theme.baseColors.grayscale[800]};
  }
`;

const DefaultInputItemsContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const DefaultInputItem = styled.div`
  width: 100%;
  display: flex;
  background: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem;
  padding: 0.8rem 2.4rem;

  ${({ theme }) => theme.typography.common.default};
  font-weight: 600;
  text-align: left;
  vertical-align: middle;
`;

const QuestionsContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 3.6rem;
`;

const AddQuestionButton = styled.button`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 0.3rem;
  padding: 0.8rem;
  width: calc(100% - 3.6rem);

  background: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px dashed ${({ theme }) => theme.baseColors.grayscale[600]};
  border-radius: 0.8rem;

  ${({ theme }) => theme.typography.heading[500]};
  color: ${({ theme }) => theme.baseColors.grayscale[950]};
  transition: all 0.2s ease-in-out;

  :hover {
    color: ${({ theme }) => theme.baseColors.purplescale[500]};
    border-color: ${({ theme }) => theme.baseColors.purplescale[500]};
  }
`;

const StepButtonsContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  gap: 2.4rem;
`;

const StepButton = styled.button`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.3rem;

  background: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem;
  padding: 0.5rem 0.8rem;

  ${({ theme }) => theme.typography.common.default};
  font-weight: 700;
  transition: all 0.2s ease-in-out;

  :hover {
    border-color: ${({ theme }) => theme.baseColors.grayscale[700]};
    box-shadow: ${({ theme }) => `0px 2px 2px ${theme.baseColors.grayscale[400]}`};
  }
`;

const ButtonContent = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 0 0.4rem;
  gap: 0.4rem;
`;

const S = {
  Wrapper,
  Section,
  SectionTitleContainer,

  DefaultInputItemsContainer,
  DefaultInputItem,

  QuestionsContainer,
  AddQuestionButton,

  StepButtonsContainer,
  StepButton,
  ButtonContent,
};

export default S;
