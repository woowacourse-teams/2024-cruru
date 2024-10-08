import S from './style';

interface StepProps {
  stepNumber: number;
  label: string;
  isSelected: boolean;
}

function Step({ stepNumber, label, isSelected }: StepProps) {
  return (
    <S.StepContainer isSelected={isSelected}>
      <S.StepNumber isSelected={isSelected}>{stepNumber}</S.StepNumber>
      <S.StepLabel isSelected={isSelected}>{label}</S.StepLabel>
    </S.StepContainer>
  );
}

interface Option {
  text: string;
  isSelected: boolean;
}

export default function RecruitmentSidebar({ options }: { options: Option[] }) {
  return (
    <S.Container>
      <S.SidebarHeader>
        <S.Title>공고 생성</S.Title>
        <S.Description>모집 공고를 작성하고 인터넷에 게시하세요.</S.Description>
      </S.SidebarHeader>
      <S.SidebarContents>
        {options.map(({ text, isSelected }, index) => (
          <Step
            // eslint-disable-next-line react/no-array-index-key
            key={index}
            stepNumber={index + 1}
            label={text}
            isSelected={isSelected}
          />
        ))}
      </S.SidebarContents>
    </S.Container>
  );
}
