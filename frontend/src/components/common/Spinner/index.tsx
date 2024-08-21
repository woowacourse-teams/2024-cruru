import type { StyleProps } from './style';
import S from './style';

export default function Spinner({ width }: StyleProps) {
  return (
    <S.Spinner width={width}>
      <S.Bounce className="bounce1" />
      <S.Bounce className="bounce2" />
      <S.Bounce />
    </S.Spinner>
  );
}
