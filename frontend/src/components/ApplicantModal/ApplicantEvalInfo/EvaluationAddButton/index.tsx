import Button from '@components/_common/atoms/Button';
import S from './style';

interface EvaluationAddButtonProps {
  onClick: () => void;
}

export default function EvaluationAddButton({ onClick }: EvaluationAddButtonProps) {
  return (
    <S.ButtonContainer>
      <Button
        type="button"
        size="fillContainer"
        color="white"
        onClick={onClick}
      >
        평가 작성
      </Button>
    </S.ButtonContainer>
  );
}
