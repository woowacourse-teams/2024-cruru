import type { StyleProps } from './style';
import S from './style';

export default function Spinner({ width, color = 'white' }: StyleProps) {
  return (
    <S.Spinner
      width={width}
      color={color}
    >
      <S.Bounce className="bounce1" />
      <S.Bounce className="bounce2" />
      <S.Bounce />
    </S.Spinner>
  );
}
