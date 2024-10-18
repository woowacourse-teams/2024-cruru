import S from './style';

interface RadioProps {
  id?: string;
  isChecked: boolean;
  isDisabled?: boolean;
  diameter?: string;
  onToggle: (checked: boolean) => void;
  required?: boolean;
  name?: string;
}

export default function Radio({
  id,
  isChecked,
  isDisabled = false,
  onToggle,
  diameter,
  required = false,
  name,
}: RadioProps) {
  return (
    <S.RadioContainer onClick={() => onToggle(!isChecked)}>
      <S.HiddenRadio
        id={id}
        type="radio"
        checked={isChecked}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) => onToggle(e.target.checked)}
        disabled={isDisabled}
        name={name}
        required={required}
      />
      <S.RadioOuter
        isDisabled={isDisabled}
        diameter={diameter}
        checked={isChecked}
      >
        <S.RadioInner
          checked={isChecked}
          isDisabled={isDisabled}
        />
      </S.RadioOuter>
    </S.RadioContainer>
  );
}
