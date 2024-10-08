import RadioOption from '../RadioOption';
import S from './style';

interface Option {
  label: string;
  value: string;
}

interface RadioFieldProps {
  options: Option[];
  selectedValue?: string;
  labelSize?: string;
  optionsGap?: string;
  onChange: (value: string) => void;
}

export default function RadioField({
  options,
  selectedValue,
  labelSize,
  optionsGap = '1.6rem',
  onChange,
}: RadioFieldProps) {
  return (
    <S.Container optionsGap={optionsGap}>
      {options.map((option) => (
        <RadioOption
          key={option.value}
          label={option.label}
          labelSize={labelSize}
          checked={selectedValue === option.value}
          onChange={() => onChange(option.value)}
        />
      ))}
    </S.Container>
  );
}
