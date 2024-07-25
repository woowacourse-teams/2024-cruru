import S from './style';

interface EvaluationHeaderProps {
  title: string;
  description: string;
}

export default function EvaluationHeader({ title, description }: EvaluationHeaderProps) {
  return (
    <S.Container>
      <S.Title>{title}</S.Title>
      <S.Description>{description}</S.Description>
    </S.Container>
  );
}
